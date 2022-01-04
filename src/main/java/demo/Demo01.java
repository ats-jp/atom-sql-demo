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

			Sandbox.resultSet(r -> {
				r.setColumnNames("id", "name", "created");
				r.addRow(1L, "name1", new Timestamp(System.currentTimeMillis()));
				r.addRow(2L, "name2", new Timestamp(System.currentTimeMillis()));
				r.addRow(3L, "name3", new Timestamp(System.currentTimeMillis()));
			});

			proxy.selectAsList().forEach(Demo01::log);

			proxy.selectAsStream().forEach(Demo01::log);

			proxy.selectAsAtom().stream().forEach(Demo01::log);

			Sandbox.resultSet(r -> {
				r.setColumnNames("id", "name", "created");
				r.addRow(1L, "name1", new Timestamp(System.currentTimeMillis()));
			});

			proxy.selectById(999).ifPresent(Demo01::log);

			proxy.insert(999, "demo customer", new Timestamp(System.currentTimeMillis()));
		});
	}

	private static void log(Object o) {
		logger.info(o.toString());
	}
}
