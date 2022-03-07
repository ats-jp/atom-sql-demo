package demo;

import jp.ats.atomsql.Utils;

public class Demo00 {

	public static void main(String[] args) throws Exception {
		Utils.loadProxyClasses().forEach(System.out::println);
	}
}
