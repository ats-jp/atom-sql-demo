package demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlParameters;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo04 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			var list = new ArrayList<Long>();

			list.add(111L);
			list.add(222L);
			list.add(333L);
			list.add(444L);

			var values = new Csv<>(list);

			proxy.selectByIds(values);

			proxy.select(p -> {
				p.ids = values;
				p.name = "%name%";
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE id IN (:ids)")
		List<DataObjectImpl> selectByIds(Csv<Long> ids);

		@Sql("SELECT * FROM customer WHERE id IN (:ids/*CSV<LONG>*/) AND name LIKE :name")
		@SqlParameters
		List<DataObjectImpl> select(Consumer<Demo04Parameters> params);

		@DataObject
		public static class DataObjectImpl {

			public long id;

			public String name;

			public LocalDateTime created;
		}
	}
}
