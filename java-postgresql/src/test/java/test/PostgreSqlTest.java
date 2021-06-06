package test;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class PostgreSqlTest {

    @Test
    public void test() {
        String createSql = "CREATE TABLE IF NOT EXISTS TEST(ID SERIAL PRIMARY KEY, VALUE VARCHAR(10))";
        String insertSql = "INSERT INTO TEST (VALUE) VALUES ('test')";

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.USER, "postgres")
                .option(ConnectionFactoryOptions.PASSWORD, "example")
                .build();
        ConnectionFactory factory = ConnectionFactories.get(options);
        Mono<Connection> conMono = Mono.from(factory.create());
        // CREATE TABLE
        {
            Mono<? extends Result> resultMono =
                    conMono.flatMap(con -> Mono.from(con.createStatement(createSql).execute()));
            Mono<? extends Integer> countMono = resultMono.flatMap(result -> Mono.from(result.getRowsUpdated()));
            System.out.println(countMono.block(Duration.ofSeconds(5)));
        }
        // INSERT
        {
            Mono<? extends Result> resultMono = conMono.flatMap(con ->
                    Mono.from(con.createStatement(insertSql).returnGeneratedValues("id").execute()));
            Mono<Tuple2<Integer, Object>> tuple2Mono = resultMono.flatMap(result ->
                    Mono.from(result.getRowsUpdated()).zipWith(Mono.from(result.map((row, __) -> row.get("id")))));
            Tuple2<Integer, Object> tuple2 = tuple2Mono.block(Duration.ofSeconds(5));
            System.out.println(tuple2);
        }
    }
}
