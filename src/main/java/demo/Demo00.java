package demo;

import jp.ats.atomsql.AtomSqlUtils;

public class Demo00 {

	public static void main(String[] args) throws Exception {
		AtomSqlUtils.loadProxyClasses().forEach(System.out::println);
	}
}
