module jp.ats.atomsql.demo {

	requires transitive jp.ats.atomsql;

	requires org.slf4j;

	exports demo;

	//リソースファイル(sql)取得のためにopenが必要
	opens demo;
}
