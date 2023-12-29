package demo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.HalfAtom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlInterpolation;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo16 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var now = new Timestamp(System.currentTimeMillis());

			Sandbox.resultSet(r -> {
				r.setColumnNames("id", "name", "created");

				r.addRow(1L, "name1", now);
				r.addRow(2L, "name2", now);
				r.addRow(3L, "name3", now);
			});

			var proxy = atomSql.of(Proxy.class);

			var main = proxy.select();

			main.put(a -> {
				a.id = proxy.forId(0);
				a.name = proxy.forName("name");
				a.created_at = proxy.forCreatedAt(LocalDateTime.now());
			}).list().forEach(d -> {
				System.out.println(d);
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql(/*@formatter:off*/"""
SELECT * FROM customer WHERE /*${id}*/ AND /*${name}*/ AND /*${created_at}*/
		"""/*@formatter:on*/)
		@SqlInterpolation
		HalfAtom<DataObjectImpl, Demo16_Proxy_select> select();

		@Sql("id = :id")
		Atom<?> forId(long id);

		@Sql("name = :name")
		Atom<?> forName(String name);

		@Sql("created_at = :createdAt")
		Atom<?> forCreatedAt(LocalDateTime createdAt);
	}

	@DataObject
	public static class DataObjectImpl {

		public long id;

		public String name;

		public LocalDateTime created;

		@Override
		public String toString() {
			return "id: " + id + ", name: " + name + ", created: " + created;
		}
	}
}
