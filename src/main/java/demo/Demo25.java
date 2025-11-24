package demo;

import java.time.LocalDateTime;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo25 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			var unionAll = proxy.unionAll();

			var base = proxy.selectByInteger(0);

			var sqls = new Atom<?>[] {
				proxy.selectByInteger(0),
				proxy.selectByInteger(0),
				proxy.selectByInteger(0),
			};

			base.joinAndConcat(unionAll, sqls).list();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("\r\nUNION ALL\r\n")
		Atom<?> unionAll();

		@Sql("SELECT * FROM customer WHERE id = :id")
		Atom<DataObjectImpl> selectByInteger(int id);

		@DataObject
		public static class DataObjectImpl {

			public long id;

			public String name;

			public LocalDateTime created;
		}
	}
}
