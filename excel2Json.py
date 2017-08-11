#
# Excel to Json tools...
#
# e.g.
# -------------------------------------
# Excel data like.
# sid  name	begin	headLevelSid nextSid banCountry
# 1	   青州	 1      1	            2       0
# 2	   兖州	 0      1	            3       1
#
# To Json data like.
#
# [
#     {
#         "sid": 1,
#         "name": "青州",
#         "begin": 1,
#         "headLevelSid": 1,
#         "nextSid": 2,
#         "banCountry": 0
#     },
#     {
#         "sid": 2,
#         "name": "兖州",
#         "begin": 0,
#         "headLevelSid": 1,
#         "nextSid": 3,
#         "banCountry": 1
#     }
# ]
#
# Version: 1.0
# LastModified: 2017-07-20
#
# Author Alan.Young

# -*- coding: utf8 -*-
import os
import string
from datetime import datetime

import sys
import xlrd
import json

from xlrd import xldate_as_tuple


def readWb2Json(filename: string):
    bk = xlrd.open_workbook(filename)
    # 获取文件表格数量
    sh = bk.sheet_by_index(0)
    n = sh.nrows
    if n < 2:
        print("表格行数不足，nrows = %s,filename = %s" % (n, sh.name))
        return
    keys = sh.row(0)
    cellLen = len(keys)
    values = []
    for rownum in range(1, n):
        data = {}
        for cellnum in range(cellLen):
            key = str(keys[cellnum].value)
            row = sh.row(rownum)
            cell = row[cellnum]
            ctype = cell.ctype
            value = cell.value
            if ctype == 2:  # 如果是数字类型
                if value % 1 == 0.0:
                    value = int(value)
            elif ctype == 3:
                # 转成datetime对象
                date = datetime(*xldate_as_tuple(value, 0))
                value = date.strftime('%Y/%d/%m %H:%M:%S')
            elif ctype == 4:
                value = True if cell == 1 else False
            ov = data[key]
            if ov is None:
                data[key] = value
            elif isinstance(ov,list):
                ov.append(value)
            else:
                data[key]=[ov,value]
        values.append(data)
    return values


def excel2json(_fromDir: string, _toDir: string):
    for parent, dirnames, filenames in os.walk(_fromDir):
        for filename in filenames:
            if filename.startswith("~$") or not (filename.endswith(".xlsx") or filename.endswith(".xls")):
                continue
            fromFile = os.path.join(parent, filename)
            mid = parent.replace(_fromDir, _toDir)
            if not os.path.exists(mid):
                os.makedirs(mid)
            tofile = os.path.join(mid, filename.split(".")[0] + ".json")
            print("inputFile = %s,outFile = %s" % (fromFile, tofile))
            data = readWb2Json(fromFile)
            jsonData = json.dumps(data, ensure_ascii=False, indent=4)
            f = open(tofile, 'w', encoding="UTF-8")
            f.write(jsonData)


if __name__ == "__main__":
    print("===================================")
    print("usages: python xxx.py fromDir toDir")
    print("===================================")
    fromDir = "./excel-sample"
    toDir = "./json-sample"
    alen = len(sys.argv)
    if alen > 2 and sys.argv[1] is not None and sys.argv[2] is not None:
        fromDir = sys.argv[1]
        toDir = sys.argv[2]
        print("Your args,fromDir=%s,toDir=%s" % (fromDir, toDir))
    else:
        print("Has not enough args, will use default,fromDir=%s,toDir=%s" % (fromDir, toDir))
    print()
    excel2json(fromDir, toDir)
