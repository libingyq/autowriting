from sqlalchemy import create_engine
import tushare as ts
from sqlalchemy.engine.url import URL
import datetime
import json
from flask import Flask, Response
from stock_library import dtw_similarity
from stock_library import get_price_list
import mysql.connector
import time
from stock_library import get_sql_engine


app = Flask(__name__)

db_url = {
    'database': "knowledge_base",  # ""ultra-nlg-knowledge-base",
    'drivername': 'mysql',
    'username': 'mysql',
    'password': '123456',
    'host': '192.168.181.159',
    'query': {'charset': 'utf8'},
}
engine = create_engine(URL(**db_url), encoding="utf8")


# mydb = mysql.connector.connect(
#         database="knowledge_base",
#         user='mysql',
#         passwd='123456',
#         host='192.168.181.159',
#     )

index_table = 'share_index'
ticker_table = 'share_ticker'
port = 3000
host = '0.0.0.0'


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
        result = {'status': 'failed', 'content': err}
        return Response(json.dumps(result), mimetype='application/json')

    index_df.drop_duplicates(subset=["code"], inplace=True)
    # index_df.drop(['preclose'], axis=1, inplace=True)
    index_df.rename(columns={'change': 'change_percent', 'preclose': 'settlement'}, inplace=True)
    index_df['time'] = current

    try:
        index_df.to_sql(name=index_table, con=engine, if_exists='append', index=False)
        print(index_df.head())
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
    tickers_df.drop(['per', 'pb', 'mktcap', 'nmc'], axis=1, inplace=True)
    tickers_df.rename(columns={'changepercent': 'change_percent', 'trade': 'close', 'turnoverratio': 'turnover'},
                      inplace=True)
    tickers_df['time'] = current

    try:
        tickers_df.to_sql(name=ticker_table, con=engine, if_exists='append', index=False)
        print(tickers_df.head())
    except Exception as err:
        err = str(err)
        print(err)
        result = {'status': 'failed', 'content': err}
        return Response(json.dumps(result), mimetype='application/json')

    result = {'status': '200'}
    return Response(json.dumps(result), mimetype='application/json')


@app.route("/DTWCompareDemo/<string:tip>/<string:table>/<string:startTime>/<string:endTime>/<string:trend>")
def dtw_fit(tip, table, startTime, endTime, trend):
    try:
        # cursor = mydb.cursor()
        engine = get_sql_engine()
    except Exception as e:
        print(str(e))
        print("sleep 2 seconds, reconnecting...")
        time.sleep(2)
        engine = get_sql_engine()

    des_query = '''select DISTINCT description from share_trend '''
    # cursor.execute(des_query)
    # query_result = cursor.fetchall()
    query_result = engine.execute(des_query)
    dtw_keywords = [val for sublist in query_result for val in sublist]

    dtw_words_detected = False

    # fun_words_detected = False
    # func_parsing = {'低开': dikai}
    # for word in func_parsing.keys():
    #     if word in trend:
    #         fun_words_detected = True
    #         func_result = func_parsing[word](start, code, table)

    for word in dtw_keywords:  # 遍历数据库中的每一个关键词
        if word in trend:  # 如果该关键词在给定的参数描述中
            dtw_words_detected = True
            dtw_scores = []

            query = "select data from share_trend where description='{}'".format(word)
            # cursor.execute(query)
            # query_result = cursor.fetchall()  # 在趋势数据库中找到标准的数据走势
            query_result = engine.execute(query)
            data_samples = [val for sublist in query_result for val in sublist]  # 标准的数据走势
            for data_sample in data_samples:  # 遍历该关键词对应所有的数据走势
                test_trend = get_price_list(startTime, endTime, tip, table, engine)

                ldict = {}
                exec("target_trend=" + data_sample, globals(), ldict)
                target_trend = ldict['target_trend']

                dtw_result, difference_score = dtw_similarity(test_trend, target_trend)
                dtw_scores.append(difference_score)

            dtw_voting = [1 if score < 20 else 0 for score in dtw_scores]

            if sum(dtw_voting) > len(dtw_voting) / 2:
                dtw_voted_result = 1
            else:
                dtw_voted_result = 0

    if dtw_words_detected:
        average_score = round(sum(dtw_scores) / float(len(dtw_scores)), 5)
        if dtw_voted_result:
            result = {'status': 'true', 'scores': dtw_scores, 'average_score': average_score}
            # return dtw_voted_result
        else:
            result = {'status': 'false', 'scores': dtw_scores, 'average_score': average_score}
        return Response(json.dumps(result), mimetype='application/json')
    else:
        print('数据库中未找到关键词')
        result = {'status': 'false', 'error': 'can not find this key work in Database'}
        return Response(json.dumps(result), mimetype='application/json')
        # return False


app.run(port=port, host=host, debug=False, threaded=True)
