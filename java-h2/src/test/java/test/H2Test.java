package test;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class H2Test {
    
    @Test
    public void test() {
        String createSql = "CREATE TABLE IF NOT EXISTS TEST(ID INTEGER NOT NULL IDENTITY PRIMARY KEY, VALUE VARCHAR(10))";
        String insertSql = "INSERT INTO TEST (VALUE) VALUES ('test')";

       // ConnectionFactory factory = ConnectionFactories.get("r2dbc:h2:mem:///example;DB_CLOSE_DELAY=-1");

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "h2")
                .option(ConnectionFactoryOptions.PROTOCOL, "mem")
                .option(ConnectionFactoryOptions.DATABASE, "example")
                .option(Option.valueOf("DB_CLOSE_DELAY"), "-1")
                .build();
        ConnectionFactory factory = ConnectionFactories.get(options);
        Mono<Connection> conMono = Mono.from(factory.create());
        // CREATE TABLE
        {
            Mono<? extends Result> resultMono =
                    conMono.flatMap(con -> Mono.from(con.createStatement(createSql).execute()));
            Mono<? extends Integer> count = resultMono.flatMap(result -> Mono.from(result.getRowsUpdated()));
            System.out.println(count.block(Duration.ofSeconds(5)));
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
