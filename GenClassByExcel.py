# -*- coding: utf8 -*-

import string
import sys

import time
import xlrd
import os

javaSourceTemplate = """
 package %s;

 import org.alan.mars.sample.Sample;
 import org.alan.mars.sample.SampleFactory;
 import org.alan.mars.sample.impl.SampleFactoryImpl;
 import com.dyuproject.protostuff.Tag;
 import java.util.*;

/**
 * Auto generate by "Python tools"
 * 
 * @Date %s
 */
 public class %s extends Sample{
    public static SampleFactory<%s> factory = new SampleFactoryImpl<>();
    public static %s get%s(int sid) {
        return factory.getSample(sid);
    }

    public static %s new%s(int sid) {
        return factory.newSample(sid);
    }
 %s
 }
"""

csSourceTemplate = """ 
using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;
using ProtoBuf;

/**
 * Auto generate by "Python tools"
 * 
 * @Date %s
 */
[ProtoContract]
public class %s
{
    [ProtoMember(1, IsRequired = true)]
    public int id;
    [ProtoMember(2, IsRequired = true)]
    public string name;
 %s
}

"""

topackage="org.alan.chess.logic.sample"

class Field:
    comment = "field comment."
    modifier = "public"
    fieldType = "String"
    fieldName = ""

    def __eq__(self, o) -> bool:
        return self.fieldName == o.fieldName

    def genField(self, i):
        return "\t@Tag(%s)\n\t// %s\n\t%s %s %s;\n" % (
            i + 3, self.comment, self.modifier, self.fieldType, self.fieldName)

    def genCsField(self, i):
        if self.fieldType == "String":
            self.fieldType = "string"
        elif self.fieldType == "boolean":
            self.fieldType = "bool"
        if self.fieldType == "ArrayList":
            self.fieldType = "List"

        return "\t[ProtoMember(%s, IsRequired = true)]\n\t// %s\n\t%s %s %s;\n" % (
            i + 3, self.comment, self.modifier, self.fieldType, self.fieldName)


class JavaSourceTemplate:
    package: string = ""
    className = ""
    fields = []

    def __init__(self):
        self.fields = []

    def genJavaSource(self):
        strFields = ""
        for i, field in enumerate(self.fields):
            strFields += field.genField(i)

        return javaSourceTemplate % (
            self.package, time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()), self.className, self.className,self.className, self.className,self.className, self.className,
            strFields)

    def genCsSource(self):
        strFields = ""
        for i, field in enumerate(self.fields):
            strFields += field.genCsField(i)
        return csSourceTemplate % (time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()), self.className, strFields)

    pass


def excel2Source(parent: string, filename: string, _javaToDir: string, _csToDir: string):
    if filename.startswith("~$") or not (filename.endswith(".xlsx") or filename.endswith(".xls")):
        return
    fullName = os.path.join(parent, filename)

    index = filename.find('.')
    pkg = filename[0: index]
    # 读取文件
    bk = xlrd.open_workbook(fullName)
    # package = fullName.replace(fromDir, "").split(".")[0].replace("/", ".")
    package = topackage+"."+pkg
    # 获取文件表格数量
    sheetlen = bk.nsheets
    for sheetnum in range(sheetlen):
        jst = JavaSourceTemplate()
        jst.package = package
        # 获取行数
        sh = bk.sheet_by_index(sheetnum)
        sheetName = sh.name
        # 得到类名
        jst.className = sheetName
        nrows = sh.nrows
        if nrows >= 3:
            fieldComment = sh.row(0)
            fieldType = sh.row(1)
            fieldNames = sh.row(2)
            cellLen = len(fieldType)
            for cellNum in range(cellLen):
                field = Field()
                field.comment = fieldComment[cellNum].value
                field.fieldType = fieldType[cellNum].value
                field.fieldName = fieldNames[cellNum].value
                if field not in jst.fields and field.fieldName not in ["sid", "name"]:
                    jst.fields.append(field)
            pass

            if _javaToDir is not None:
                javaSource = jst.genJavaSource()
                print(javaSource)
                path = str(package.replace(".", "/")) + "/"
                sourcePath = _javaToDir + path
                if not os.path.exists(sourcePath):
                    os.makedirs(sourcePath)
                sourceFileName = sourcePath + sheetName + ".java"
                file = open(sourceFileName, mode="w", buffering=1024, encoding="UTF-8")
                file.write(javaSource)
            if _csToDir is not None:
                csSource = jst.genCsSource()
                csSourcePath = _csToDir
                if not os.path.exists(csSourcePath):
                    os.makedirs(csSourcePath)
                cdSourceFileName = csSourcePath + sheetName + ".cs"
                file2 = open(cdSourceFileName, mode="w", buffering=1024, encoding="UTF-8")
                file2.write(csSource)


def excelPath2Source(_fromDir: string, _javaToDir: string, _csToDir: string):
    for parent, dirnames, filenames in os.walk(_fromDir):
        for filename in filenames:
            excel2Source(parent, filename, _javaToDir, _csToDir)


if __name__ == "__main__":
    print("===================================")
    print("usages: python xxx.py fromDir javaToDir csToDir")
    print("===================================")
    fromDir = "./resources/sample/"
    toJavaDir = "./src/main/java/"
    # toCsDir = "E:/gen/Assets/Scripts/work/"
    toCsDir = None
    alen = len(sys.argv)
    if alen > 1 and sys.argv[1] is not None:
        fromDir = sys.argv[1]
    if alen > 2:
        toJavaDir = sys.argv[2]
    if alen > 3:
        toCsDir = sys.argv[3]
    print("use args,fromDir=%s,toJavaDir=%s,toCsDir=%s" % (fromDir, toJavaDir, toCsDir))
    print()
    excelPath2Source(fromDir, toJavaDir, toCsDir)
