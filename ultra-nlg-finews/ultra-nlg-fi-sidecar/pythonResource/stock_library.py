import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sqlalchemy import create_engine
from sqlalchemy.engine.url import URL
import mysql.connector
import datetime
from pydtw import dtw1d
from sklearn.preprocessing import StandardScaler


def get_sql_engine():
    db_url = {
        'database': "knowledge_base",  # ""ultra-nlg-knowledge-base",
        'drivername': 'mysql',
        'username': 'mysql',
        'password': '123456',
        'host': '192.168.181.159',
        'query': {'charset': 'utf8'},
    }
    # return URL(**db_url)
    return create_engine(URL(**db_url), encoding="utf8")


def dtw_similarity(test_trend, target_trend, threshold=20):
    standard_test_trend = StandardScaler().fit_transform(np.array(test_trend).reshape([-1, 1]))
    standard_target_trend = StandardScaler().fit_transform(np.array(target_trend).reshape([-1, 1]))

    cost_matrix, _1, _2 = dtw1d(standard_test_trend.reshape([1, -1])[0], standard_target_trend.reshape([1, -1])[0])
    difference = np.asarray(cost_matrix)[-1, -1]
    # return difference
    print(difference)
    if difference < threshold:
        return True,difference
    else:
        return False,difference


def dtw_score(test_trend, target_trend):
    standard_test_trend = StandardScaler().fit_transform(np.array(test_trend).reshape([-1, 1]))
    standard_target_trend = StandardScaler().fit_transform(np.array(target_trend).reshape([-1, 1]))

    cost_matrix, _1, _2 = dtw1d(standard_test_trend.reshape([1, -1])[0], standard_target_trend.reshape([1, -1])[0])
    difference = np.asarray(cost_matrix)[-1, -1]
    return difference

# 获得目标交易日的价格列表
def get_price_list(start, end, code, table, engine):
    # mycursor = engine
    current_open_query = '''
        select close from {} where time between '{}' 
        and '{}' 
        and code='{}' 
    	ORDER BY time ASC
        '''.format(table, start, end, code)

    try:
        # mycursor.execute(current_open_query)
        # current_query_result = mycursor.fetchall()
        current_query_result = engine.execute(current_open_query)
    except mysql.connector.errors.InterfaceError as e:
        print(str(e))
    current_open = [val for sublist in current_query_result for val in sublist]

    return current_open


# 获得上一个交易日的收盘价
def get_last_close_price(start, code, table):
    return


# 低开
def dikai(cursor, start='2018-10-18 09:30:00', code='000001', table='share_index',):
    mycursor = cursor

    current_end_time = datetime.datetime.strptime(start.split(' ')[0], "%Y-%m-%d") + datetime.timedelta(hours=16)
    current_end_time = datetime.datetime.strftime(current_end_time, "%Y-%m-%d %H:%M:%S")
    current_open_query = '''
    select time, close from {} where time between '{}' 
    and '{}' 
    and code='{}' 
	ORDER BY time ASC
	LIMIT 1
    '''.format(table, start, current_end_time, code)
    # 上一个交易日
    previous_start = datetime.datetime.strptime(start.split(' ')[0], "%Y-%m-%d") - datetime.timedelta(days=1)
    previous_end = previous_start + datetime.timedelta(hours=16)

    previous_start = datetime.datetime.strftime(previous_start, "%Y-%m-%d %H:%M:%S")
    previous_end = datetime.datetime.strftime(previous_end, "%Y-%m-%d %H:%M:%S")

    previous_close_query = '''
    select time, close from {} where time between '{}' 
    and '{}' 
    and code='{}' 
	ORDER BY time DESC
	LIMIT 1
    '''.format(table, previous_start, previous_end, code)
    try:
        mycursor.execute(current_open_query)
        current_query_result = mycursor.fetchall()

        mycursor.execute(previous_close_query)
        previous_query_result = mycursor.fetchall()
    except mysql.connector.errors.InterfaceError as e:
        print(str(e))
    current_open = current_query_result[0][1]
    previous_close = previous_query_result[0][1]

    if current_open < previous_close:
        return True
    else:
        return False

if __name__ == "__main__":
    pass
# dikai()
'''
daily_file = pd.read_csv("daily_sample.csv")
minute_file = pd.read_csv("minute_sample.csv")
x = np.arange(100) / 10


def fluctuating_increse(series, time1, time2):
    average = np.average(series)
    if series[-1] > series[0] and (time1 - average) * (time2 - average) < 0:
        print("震荡上扬")
        return True
    else:
        return False


def f(X):
    return np.sin(X) + 0.5 * x


y = f(x)
fluctuating_increse(y, 2, 4)
plt.plot(x, y)
plt.show()


def significanly_increse(series, time1, time2):
    init = series[0]
    if init > series[time1] > series[time2] > series[-1]:
        print("一路上扬")
        return True
    else:
        return False


def f(X):
    return 0.1 + x ** 0.5


y = f(x)
significanly_increse(y, 2, 4)
plt.plot(x, y)
plt.show()


def increase_afternoon(series):
    mid = int(series.shape[0] / 2)
    selected_points = random.sample(range(mid + 1, series.shape[0]), 2)
    selected_points.sort()
    if series[mid] < series[selected_points[0]] < series[selected_points[1]] < series[-1]:
        print('午后拉升')
        return True
    else:
        return False


def f(X):
    return 8 * (x / 60) ** 4


y = f(x)
increase_afternoon(y)
plt.plot(x, y)
plt.show()


def new_peak(series, local_max):
    max_value = max(series)
    if max_value >= local_max:
        print('新高')
        return True
    else:
        return False


def incease_limit(series):
    max_value = max(series)
    if max_value / series[0] >= 0.095:
        print('涨停')
        return True
    else:
        return False


def slightly_incease(series):
    max_value = max(series)
    if max_value / series[0] <= 0.01:
        print('小幅上涨')
        return True
    else:
        return False


def f(X):
    return 0.01 * np.sin(X) + 0.1 * x ** 0.25


y = f(x)
slightly_incease(y)
plt.plot(x, y)
plt.show()


def slightly_incease(series):
    min_value = min(series)
    if series[0] > min_value and series[-1] > min_value:
        print('触底反弹')
        return True
    else:
        return False


def f(X):
    return -0.8 + (x / 2 - 3) ** 2


y = f(x)
slightly_incease(y)
plt.plot(x, y)
plt.show()


def f(X):
    return -0.8 + (x / 2 - 3) ** 2


y = f(x)
slightly_incease(y)
plt.plot(x, y)
plt.show()


def steady_incease(series):
    min_value = min(series)
    if series[0] > min_value and series[-1] > min_value:
        print('增速放缓')
        return True
    else:
        return False


def over_benchmark(benchmark, series):
    if max(series) > benchmark:
        print("突破")
        return True
    else:
        return False


# over_benchmark(10,daily_file['close'])


def lower_benchmark(benchmark, series):
    if max(series) > benchmark:
        print("跌破")
        return True
    else:
        return False


# lower_benchmark(10,daily_file['close'])

def high2low(pre_close, time1, series):
    init = series[0]
    if init > pre_close and series[time1] < init and series[-1] < series[time1]:
        print("高开低走")
        return True
    else:
        return False


# def f(X):
#     return 1/(1+np.e**X)-0.3
# y = f(x)
# high2low(0,2,y)
# plt.plot(x,y)
# plt.show()

def low2high(pre_close, time1, series):
    init = series[0]
    if init < pre_close and series[time1] > init and series[-1] > series[time1]:
        print("低开高走")
        return True
    else:
        return False

'''
# def f(X):
#     return 1/(1+np.e**(-X))-0.7
# y = f(x)
# low2high(0,2,y)
# plt.plot(x,y)
# plt.show()
