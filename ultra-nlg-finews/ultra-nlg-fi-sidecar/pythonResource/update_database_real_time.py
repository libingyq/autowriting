from sqlalchemy import create_engine
import tushare as ts
from sqlalchemy.engine.url import URL
import datetime
import json
from flask import Flask, Response


app = Flask(__name__)


db_url = {
        'database': "knowledge_base",#""ultra-nlg-knowledge-base",
        'drivername': 'mysql',
        'username': 'mysql',
        'password': '123456',
        'host': '192.168.181.159',
        'query': {'charset': 'utf8'},
    }

engine = create_engine(URL(**db_url), encoding="utf8")
index_table = 'share_index'
ticker_table = 'share_ticker'
port = 3000
host = '0.0.0.0'
@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')


@app.route("/DTWCompareDemo/<string:tip>/<string:table>/<string:startTime>/<string:endTime>/<string:trend>")
def DTWCompareDemo(tip,table,startTime,endTime,trend):
    print(tip,table,startTime,endTime,trend)
    result = {'status': 'ture'}
    return Response(json.dumps(result), mimetype='application/json')



# 大盘指数行情列表 上证指数 深证成指 创业板 等， API 未提供换手率turnover和价格变化price_change数据
# 成功获取数据并上传到数据库，返回status：200，如果失败，返回'status':'failed', 'content':错误信息
@app.route("/indeces")
def get_indeces():
    current = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    try:
        index_df = ts.get_index()
    except Exception as err:
        err = str(err)
        print(err)
        result = {'status':'failed', 'content':err}
        return Response(json.dumps(result), mimetype='application/json')

    index_df.drop_duplicates(subset=["code"], inplace=True)
    index_df.drop(['preclose'], axis=1, inplace=True)
    index_df.rename(columns={'change': 'change_percent'}, inplace=True)
    index_df['time'] = current

    try:
        index_df.to_sql(name=index_table, con=engine, if_exists='append', index=False)
    except Exception as err:
        err = str(err)
        print(err)
        result = {'status': 'failed', 'content': err}
        return Response(json.dumps(result), mimetype='application/json')

    result = {'status': '200'}
    return Response(json.dumps(result), mimetype='application/json')

# 每只股票实时行情， API 未提供价格变化price_change数据
# 成功获取数据并上传到数据库，返回status：200，如果失败，返回'status':'failed', 'content':错误信息
# 为保证数据格式一致，api中trade:现价更名为close
@app.route("/tickers")
def get_tickers():
    current = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    try:
        tickers_df = ts.get_today_all()
    except Exception as err:
        err = str(err)
        print(err)
        result = {'status': 'failed', 'content': err}
        return Response(json.dumps(result), mimetype='application/json')

    tickers_df.drop_duplicates(subset=["code"], inplace=True)
    tickers_df.drop(['per', 'pb', 'mktcap', 'nmc', 'settlement'], axis=1, inplace=True)
    tickers_df.rename(columns={'changepercent': 'change_percent','trade':'close','turnoverratio': 'turnover'}, inplace=True)
    tickers_df['time'] = current

    try:
        tickers_df.to_sql(name=ticker_table, con=engine, if_exists='append', index=False)
    except Exception as err:
        err = str(err)
        print(err)
        result = {'status': 'failed', 'content': err}
        return Response(json.dumps(result), mimetype='application/json')

    result = {'status': '200'}
    return Response(json.dumps(result), mimetype='application/json')

app.run(port=port, host=host)