package demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

			{
				var list = new ArrayList<Long>();

				list.add(111L);
				list.add(222L);
				list.add(333L);

				var values = Csv.of(list);

				proxy.selectByIds(values);

				proxy.select(p -> {
					p.ids = values;
					p.name = "%name%";
				});
			}

			{
				var list = new ArrayList<Long>();

				list.add(444L);
				list.add(555L);
				list.add(666L);

				var values = Csv.of(list.stream());

				proxy.selectByIds(values);

				proxy.select(p -> {
					p.ids = values;
					p.name = "%name%";
				});
			}

			{
				var values = Csv.of(777L, 888L, 999L);

				proxy.selectByIds(values);

				proxy.select(p -> {
					p.ids = values;
					p.name = "%name%";
				});
			}
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE id IN (:ids)")
		List<DataObjectImpl> selectByIds(Csv<Long> ids);

		@Sql("SELECT * FROM customer WHERE id IN (:ids/*CSV<LONG>*/) AND name LIKE :name/*STRING*/")
		@SqlParameters
		List<DataObjectImpl> select(Consumer<Demo04Parameters1> params);

		@Sql("SELECT * FROM customer WHERE id IN (:ids) AND name LIKE :name")
		@SqlParameters
		List<DataObjectImpl> dynamicTypeDecision(Consumer<Demo04Parameters2> params);

		@DataObject
		public static record DataObjectImpl(long id, Optional<String> name, LocalDateTime created) {}
	}
}
