package demo;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.SimpleConfigure;

public class Demo01 {

	static final Logger logger = LoggerFactory.getLogger(Demo01.class);

	public static void main(String[] args) throws Exception {
		Sandbox.execute(new SimpleConfigure(false, null)/*SQLログを出力しないように設定*/, atomSql -> {
			var proxy = atomSql.of(Demo01Proxy.class);

			var now = new Timestamp(System.currentTimeMillis());

			Sandbox.resultSet(r -> {
				r.setColumnNames("id", "name", "created");

				r.addRow(1L, "name1", now);
				r.addRow(2L, "name2", now);
				r.addRow(3L, "name3", now);
			});

			proxy.selectAsList().forEach(Demo01::log);

			proxy.selectAsStream().forEach(Demo01::log);

			proxy.selectAsAtom().stream().forEach(Demo01::log);

			Sandbox.resultSet(r -> {
				r.setColumnNames("id", "name", "created");
				r.addRow(1L, "name1", now);
			});

			proxy.selectById(999).ifPresent(Demo01::log);

			proxy.insert(999, "demo customer", now.toLocalDateTime());
		});
	}

	private static void log(Object o) {
		logger.info(o.toString());
	}
}
