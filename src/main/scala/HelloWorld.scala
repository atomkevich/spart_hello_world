
import net.ruippeixotog.scalascraper.browser.Browser
import org.apache.hadoop.hbase.client.{HBaseAdmin, Put, HTable}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.hbase.{HColumnDescriptor, TableName, HTableDescriptor, HBaseConfiguration}
// define main method (scala entry point)
object HelloWorld {
  def main(args: Array[String]) {

    // initialise spark context
    val conf = new SparkConf().setMaster("local").setAppName("My App")
    val sc = new SparkContext(conf)


    val browser = new Browser
    val links = browser.get("http://www.doandodge.net/").getElementsByTag("a").iterator()

    val urls = inject("home/alina/spark/input.txt", sc)

    val hbaseTableName = "nutch3"
    val hbaseConf = HBaseConfiguration.create()


    // Other options for configuring scan behavior are available. More information available at
    // http://hbase.apache.org/apidocs/org/apache/hadoop/hbase/mapreduce/TableInputFormat.html
    hbaseConf.set(TableInputFormat.INPUT_TABLE, hbaseTableName)

       // Initialize hBase table if necessary
       val admin = new HBaseAdmin(hbaseConf)
       if (!admin.isTableAvailable(hbaseTableName)) {
         val tableDesc = new HTableDescriptor(TableName.valueOf(hbaseTableName))
         tableDesc.addFamily(new HColumnDescriptor(Bytes.toBytes("links")));
         admin.createTable(tableDesc)
       }

       val myTable = new HTable(hbaseConf, TableName.valueOf(hbaseTableName))
    while(links.hasNext) {
         val p = new Put(Bytes.toBytes("row1")).add(Bytes.toBytes("links"),
           Bytes.toBytes("name"), Bytes.toBytes(links.next().attr("href")))
         myTable.put(p)
       }
       myTable.flushCommits();

       val hBaseRDD = sc.newAPIHadoopRDD(hbaseConf, classOf[TableInputFormat],
         classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
         classOf[org.apache.hadoop.hbase.client.Result])


    sc.stop()

  }

  def inject(text: String, sc: SparkContext): List[String] = {
    val file = sc.textFile("/home/alina/spark.txt")
    file.flatMap(line => line.split(System.getProperty("line.separator"))).collect().toList
  }

  def fetch(fetchUrls: List[String]): Unit = {

  }
}