package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.GrabLogDao;
import com.dinfo.fi.dto.GrabStatusDto;
import com.dinfo.fi.entity.GrabLog;
import com.dinfo.fi.enums.GrabStatusType;
import com.dinfo.fi.enums.ShareType;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GrabLogService {

    @Autowired
    GrabLogDao grabLogDao;


    /**
     * 查询startTime后的数据爬取状态和最后的爬取状态的的并集，保留最新结果
     *
     * @return
     */
    public Optional<Map<ShareType, GrabStatusDto>> getGrabStatus(String startTime) {
        String SQLByTime = "select * from " + grabLogDao.getTable() + " where grab_time >='" + startTime + "' ";
        String SQLBySharetype = "SELECT * FROM " + grabLogDao.getTable() + " t1 where id in (select max(id) from " + grabLogDao.getTable() + " t2 group by t2.data_type) order by grab_time desc";
        List<GrabStatusDto> query = grabLogDao.getJdbcTemplate().query(SQLByTime + " UNION " + SQLBySharetype, new RowMapper<GrabStatusDto>() {
            @Override
            @Nullable
            public GrabStatusDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return changeResultSetToGrabStatusDto(rs, rowNum);
            }
        });

        if (CollectionUtils.isEmpty(query)) {
            return Optional.empty();
        }
        Map<ShareType, GrabStatusDto> res = mergeGrabStatus(query);
        return Optional.of(res);
    }


    /**
     * 使用时间相对较近的状态覆盖较远的状态，使用成功状态覆盖失败状态
     */
    private Map<ShareType, GrabStatusDto> mergeGrabStatus(List<GrabStatusDto> query) {
        Map<ShareType, GrabStatusDto> res = new HashMap<>();
        for (GrabStatusDto grabStatusDto : query) {
            if (!res.keySet().contains(grabStatusDto.getShareType())) {
                res.put(grabStatusDto.getShareType(), grabStatusDto);
            } else if (res.keySet().contains(grabStatusDto.getShareType()) && !res.get(grabStatusDto.getShareType()).getGrabStatusType().equals(GrabStatusType.OK) && !res.get(grabStatusDto.getShareType()).getGrabStatusType().equals(GrabStatusType.TRUE)) {
                // 若状态为成功，才覆盖掉
                if (grabStatusDto.getGrabStatusType().equals(GrabStatusType.OK) || grabStatusDto.getGrabStatusType().equals(GrabStatusType.TRUE)) {
                    res.put(grabStatusDto.getShareType(), grabStatusDto);
                }
            }
        }
        return res;
    }

    /**
     * 获取抓取的日志状态，查询的时间为10分钟前到现在或最后一次抓取
     *
     * @return
     */
    public Response<Map<ShareType, GrabStatusDto>> getGrabStatus() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = TimeUtils.getLastTranscationDateTime();
        Optional<Map<ShareType, GrabStatusDto>> grabStatus = getGrabStatus(time.format(dateTimeFormatter));
        if (grabStatus.isPresent()) {
            grabStatus.get().forEach((k, v) -> {
                if (LocalDateTime.parse(v.getGrabTime(), dateTimeFormatter).isBefore(time)) {
                    v.setGrabStatusType(GrabStatusType.FAILED);
                }
            });
            return Response.ok(grabStatus.get());
        }
        return Response.notOk("数据不存在");

    }


    private GrabStatusDto changeResultSetToGrabStatusDto(ResultSet rs, int rowNum) throws SQLException {
        GrabStatusDto grabStatusDto = GrabStatusDto.builder()
                .grabStatusType(GrabStatusType.getEnumType(rs.getString(3)))
                .grabTime(rs.getString(2))
                .shareType(ShareType.getEnumType(rs.getString(6)))
                .build();
        grabStatusDto.setGrabTime(grabStatusDto.getGrabTime().substring(0, grabStatusDto.getGrabTime().lastIndexOf(".")));
        return grabStatusDto;
    }

    private GrabLog changeResultSetToGrabLog(ResultSet rs, int rowNum) throws SQLException {
        GrabLog grabLog = new GrabLog();
        grabLog.setId(rs.getInt(1));
        grabLog.setGrabTime(rs.getTimestamp(2));
        grabLog.setStatus(rs.getString(3));
        grabLog.setContent(rs.getString(4));
        grabLog.setRetry(rs.getInt(5));
        grabLog.setDataType(rs.getString(6));
        return grabLog;
    }

}
