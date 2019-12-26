package com.xyf.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.functions._


object StructuredStreamingDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("StructuredStreamingDemo")
      .getOrCreate()
    import spark.implicits._
    val schema = new StructType()
      .add(StructField("id", StringType))
      .add(StructField("mobile", StringType))
      .add(StructField("email", StringType))
      .add(StructField("created_time", TimestampType))
      .add(StructField("created_ip", StringType))

    val data = Seq(
      "{'id': '11111', 'mobile': '18212341234', 'email': null, 'created_time': '2019-01-03 15:40:27', 'created_ip': '11.122.68.106'}"
    )
    val ds = data.toDS()
    val ds1 = ds
      .selectExpr("CAST(value AS STRING) as json")
      .select(from_json(col("json"),schema=schema).as("data"))
      .selectExpr("data.*")

  }
}
