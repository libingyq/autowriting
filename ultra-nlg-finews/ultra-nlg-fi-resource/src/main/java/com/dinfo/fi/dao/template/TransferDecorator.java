package com.dinfo.fi.dao.template;

import com.dinfo.common.enums.DatabaseType;
import com.dinfo.common.model.Page;
import com.dinfo.common.model.Paging;
import com.dinfo.common.util.ReflectUtil;
import com.dinfo.core.OrdmDataSource;
import com.dinfo.core.query.Query;
import com.dinfo.rdbms.DbmsDao;
import com.dinfo.rdbms.OrmDatasourceManager;
import com.dinfo.rdbms.RdbmsJdbcTemplate;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Date:2017/10/10</p>
 * <p>Module:</p>
 * <p>Description: 用作jdbcBaseDao与ordm框架之间的旧代码兼容转换。（新开发代码请直接使用ordm）</p>
 * <p>Remark: 作用是不改变原有旧代码前提下将数据库操作交给ordm框架，该转换模式使用装饰模式</p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
public abstract class TransferDecorator<T> implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(TransferDecorator.class);

    private String daoClassName = getClass().getSimpleName() + " ";

    public DbmsDao<T> dbmsDao ;

    private Class<T> entityClass = ReflectUtil.getGenericType(getClass(), 0);


    public DbmsDao<T> getDbmsDao() {
        return dbmsDao;
    }

    public void setDbmsDao(DbmsDao<T> dbmsDao) {
        this.dbmsDao = dbmsDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        final DataSource dataSource = getDataSource();
        if(dataSource == null){
            throw new RuntimeException("DataSource对象为空，检查数据源相关配置");
        }
        //获取数据库类型
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        String databaseProductName = metaData.getDatabaseProductName();
        connection.close();

        dbmsDao = new DbmsDao<T>(entityClass) {
            @Override
            public OrdmDataSource<DataSource> getOrdmDataSource() throws Exception {
                return OrmDatasourceManager.get(dataSource, databaseProductName);
//        return OrmDatasourceManager.getDefaultDataSource();
            }
        };
    }




    public TransactionTemplate getTransactionTemplate() {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(getDataSource());
        return new TransactionTemplate(platformTransactionManager);
    }

    public abstract DataSource getDataSource();


    public String getTable() {
        return dbmsDao.getTable();
    }

    public OrdmDataSource<DataSource> getDs() {
        return dbmsDao.getDs();
    }
    /**
     * 查询list
     *
     * @param sql 查询sql
     * @param map 参数map
     * @return 返回的list
     * @throws DataAccessException
     */
    public List<T> getList(String sql, Map<String, Object> map) {
        try {
            List<T> result = getJdbcTemplate().query(sql, map, new BeanPropertyRowMapper<T>(entityClass));

            if (CollectionUtils.isEmpty(result)) {
                return Lists.newArrayList();
            }
            return result;
        } catch (Throwable e) {
            throw new RuntimeException("getlist error", e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCountSql(String sql){
        Pattern pattern=Pattern.compile("((?i)select)(.*)((?i)from)(.*)");
        Matcher matcher=pattern.matcher(sql);
        boolean matchResult=matcher.find();
        Preconditions.checkArgument(matchResult,"无效的sql语句:"+sql);
        return matcher.group(1)+" count(*) "+matcher.group(3)+matcher.group(4);
    }

    /**
     * 分页查询获取列表
     *
     * @param sql  sql语句
     * @param page page对象
     * @return 分页结果
     */
    public Paging<T> getList(String sql, Map<String, Object> map, Page page) {
        try {
            final int offset = page.getOffset();
            String query = "";
            if (dbmsDao.getDs().getSourceType().equalsIgnoreCase(DatabaseType.MYSQL.getName())) {
                query = sql + " LIMIT " + page.getLimit() + " OFFSET " + offset;
            } else if (dbmsDao.getDs().getSourceType().equalsIgnoreCase(DatabaseType.ORACLE.getName())) {
                query = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A WHERE ROWNUM <= " + (offset + page.getLimit()) + " ) WHERE RN > " + offset;
            }
            List<T> list = getList(query, map);

            int count = getJdbcTemplate().queryForObject(getCountSql(sql), map, Integer.class);
            return new Paging<T>(new Long(count), list);
        } catch (Throwable e) {
            throw new RuntimeException("getlist error", e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public T getEntity(Object id) {

        try {
            return dbmsDao.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public int getCount(String sql,Map<String,Object> paramMap){
        try {
            int count = getJdbcTemplate().queryForObject(sql, paramMap,Integer.class);

            return count;
        }catch (Throwable e){
            throw new RuntimeException("getCount error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取满足条件的数量
     * @param sql 查询sql
     * @return 记录数
     */
    public int getCount(String sql){
        try {
            int count = getJdbcTemplate().queryForObject(sql, Maps.<String, Object>newHashMap(),Integer.class);

            return count;
        }catch (Throwable e){
            throw new RuntimeException("getCount error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新语句
     * @param sql 更新sql
     * @param map 参数map
     * @return 更新的数量
     */
    public int update(String sql,Map<String,Object> map)  {
        try {
            int i = getJdbcTemplate().update(sql, map);

            return i;
        }catch (Throwable e){
            throw new RuntimeException("update error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public RdbmsJdbcTemplate getJdbcTemplate() {
        try {
            return dbmsDao.getJdbcTemplate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Boolean update(T model) {
        try {
            return dbmsDao.updateById(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public T insert(T model){
        try {
            return dbmsDao.insert(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<T> batchInsert(List<T> model){
        try {
            return dbmsDao.insert(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 查询一条记录,如果返回结果大于1,抛异常
     * @param sql 查询sql
     * @param params 参数map
     * @return 满足条件的实体
     */
    public T getOne(String sql, Map<String, Object> params) {
        try {
            List<T> list = getList(sql, params);

            T t = null;
            if (list != null && list.size() > 0) {
                Preconditions.checkArgument(list.size() == 1, "get Many Entity");
                t = list.get(0);
            }
            return t;
        }catch (Throwable e){
            throw new RuntimeException("getOne error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteByPrimary(Object id){
        try {
            return dbmsDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 更新语句
     * @param sql 更新sql
     * @param map 参数map
     * @return 是否成功
     */
    public Boolean updateOne(final String sql,final  Map<String,Object> map)  {
        try {
            Boolean result=getTransactionTemplate().execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    int i = getJdbcTemplate().update(sql, map);
                    if(i<0 && i>=1){
                        throw  new RuntimeException("updateOne is error:update count:"+i);
                    }
                    return Boolean.TRUE;
                }
            });

            return result;
        }catch (Throwable e){
            throw new RuntimeException("update error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询list
     * @param sql 查询sql
     * @param model 实体bean
     * @return 返回的list
     */
    public List<T> getList(String sql,T model) {
        try {
            List<T> result = getJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(model), new BeanPropertyRowMapper<T>(entityClass));

            if(CollectionUtils.isEmpty(result)){
                return Lists.newArrayList();
            }
            return result;
        }catch (Throwable e){
            throw new RuntimeException("getlist error",e);
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<T> insert(List<T> models){
        try {
            return dbmsDao.insert(models);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean deleteById(Object id){
        try {
            return dbmsDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean deleteByIds(List<Object> ids){
        try {
            return dbmsDao.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean updateById(T model){
        try {
            return dbmsDao.updateById(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Boolean updateByIds(List<T> models){
        try {
            return dbmsDao.updateByIds(models);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public T queryById(Object id){
        try {
            return dbmsDao.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> queryListByIds(List<Object> ids){
        try {
            return dbmsDao.queryListByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> queryListByConditions(Query query){
        return dbmsDao.queryListByConditions(query);
    }

    public List<T> queryPageByConditions(Query query, com.dinfo.core.Page page){
        return dbmsDao.queryPageByConditions(query,page);
    }

    public Long getCountByConditions(Query query){
        try {
            return dbmsDao.getCountByConditions(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> queryListByIdRange(Object startId, Object endId){
        try {
            return dbmsDao.queryListByIdRange(startId,endId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> queryList(){
        try {
            return dbmsDao.queryList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> queryPage(com.dinfo.core.Page page){
        try {
            return dbmsDao.queryPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getCount(){
        try {
            return dbmsDao.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                getDataSource().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see com.dinfo.core.OrdmSourceType;
     * @return
     */
    public String getSourceType(){
    	return dbmsDao.getDs().getSourceType();
    }

}
