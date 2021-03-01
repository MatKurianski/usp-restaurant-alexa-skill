package adapter.input;

import com.kurianski.restaurant.adapter.handler.ApplicationStreamHandler;
import com.kurianski.restaurant.adapter.service.PegarRefeicaoService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

public class CardapioWebScraperAdapterTest {

    @Test
    public void test() throws IOException {
        PegarRefeicaoService pegarRefeicaoService = new PegarRefeicaoService();
        System.out.println(pegarRefeicaoService.execute(LocalDate.now().getDayOfWeek()));
    }
}
