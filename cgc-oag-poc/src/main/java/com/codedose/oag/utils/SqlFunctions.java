package com.codedose.oag.utils;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.*;

/**
 * Functions used in expressions, to make expression easier to read and write
 */
public final class SqlFunctions {


    public static Column intLength(Column column) {
        return length(column.cast(DataTypes.StringType));
    }


}
