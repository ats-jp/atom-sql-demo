package demo;

import java.util.List;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo22 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			Sandbox.resultSet(r -> {
				r.setColumnNames("bytes");

				r.addRow("bytes1".getBytes());
				r.addRow("bytes2".getBytes());
				r.addRow("bytes3".getBytes());
			});

			proxy.select().forEach(o -> {
				System.out.println(new String(o.bytes));
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		List<DataObjectImpl> select();

		@DataObject
		public static class DataObjectImpl {

			public byte[] bytes;
		}
	}
}
