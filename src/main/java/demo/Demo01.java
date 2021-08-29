package demo;

import java.sql.Timestamp;

import jp.ats.atomsql.Sandbox;

public class Demo01 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Demo01Proxy.class);

			proxy.selectAsList();

			proxy.selectAsStream();

			proxy.selectAsAtom();

			proxy.selectById(999);

			proxy.insert(999, "demo customer", new Timestamp(System.currentTimeMillis()));
		});
	}
}
