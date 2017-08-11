# 该模块用来验证wb 数据的范围与关联
# -*- coding: utf8 -*-
import xlrd
import os

# fromDir = input()

fromDir = "E:/测试文件/"

# 定义一个字典存入所有数据，以列为单位存储
fieldsMap = {}


# 检查数据范围
def checkRange(fieldName="", colValid="", colValues=[]):
    if not colValid.startswith('#'):
        return
    strMinmax = colValid[1:].split('~')
    fmin = float(strMinmax[0])
    fmax = float(strMinmax[1])
    for value in colValues:
        try:
            if fmin > value or fmax < value:
                print("数据校验范围失败，fieldName=", fieldName, ",value=", value, ",min=", fmin, ",max=", fmax)

        except IOError:
            print("数据类型配置问题，fieldName=", fieldName, ",value=", value, ",min=", fmin, ",max=", fmax)


# 校验数据关联
def checkLink(fieldName="", colValid="", colValues=[]):
    if not colValid.startswith('$'):
        return
    linkFieldName = colValid[1:]
    if linkFieldName not in fieldsMap.keys():
        print("数据关联验证失败，找不到关联字段，fieldName=", fieldName, ",linkFieldName=", linkFieldName)
        return
    for value in colValues:
        try:
            linkValues = fieldsMap[linkFieldName][1]
            if value not in linkValues:
                print("数据关联验证失败，fieldName=", fieldName, ",value=", value, ",colValid=", linkFieldName)

        except IOError:
            print("数据关联验证失败，fieldName=", fieldName, ",value=", value, ",colValid=", linkFieldName)


for parent, dirnames, filenames in os.walk(fromDir):
    for filename in filenames:
        if filename.startswith("~$"):
            continue
        fullName = os.path.join(parent, filename)
        print(fullName)
        # 读取文件
        bk = xlrd.open_workbook(fullName)
        # 相对路径
        package = fullName.replace(fromDir, "").split(".")[0].replace("/", ".")
        # 获取文件表格数量
        sheetlen = bk.nsheets
        for sheetnum in range(sheetlen):
            # 获取行数
            sh = bk.sheet_by_index(sheetnum)
            sheetName = sh.name
            print(sheetName)
            # 表格的行数
            nrows = sh.nrows
            # 表格的列数
            ncols = sh.ncols
            if nrows < 4:
                print("表格数据不齐，文件名 %s,表格名 %s" % (fullName, sheetName))
                continue
            pass

            # 按列遍历表格，把每一列的数据读入到列表中
            for colNum in range(ncols):
                # 列名
                colName = sh.cell_value(2, colNum)
                fullColName = package + "." + sheetName + "." + colName
                # 数据验证串
                colValid = str(sh.cell_value(3, colNum))
                # 本列的所有数据
                colValues = sh.col_values(colNum, 4)
                # 定义一个2元祖
                tup2 = (colValid, colValues)
                fieldsMap[fullColName] = tup2
pass

for key, value in fieldsMap.items():
    colValid = value[0]
    colValues = value[1]
    if colValid.startswith('#'):
        checkRange(key, colValid, colValues)
    elif colValid.startswith('$'):
        checkLink(key, colValid, colValues)
    else:
        # print("没有配置验证,fieldName=" + key)
        pass
