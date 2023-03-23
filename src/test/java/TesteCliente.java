import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente = "http://localhost:8080/";
    String endpointCliente = "cliente";

    //get
    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar, então a lista deve estar vazia")
    public void pegaTodosClientes () {

        String respostaEsperada = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(enderecoApiCliente)
        .then()
                .statusCode(200)
                .assertThat().body(new IsEqual<>(respostaEsperada));

    }
    //post
    @Test
    @DisplayName("Quando cadastrar um clinte, então ele deve estar disponivel no resultado")
    public void cadastraCliente() {

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 25,\n" +
                "  \"nome\": \"Minnie Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"1004\":{\"nome\":\"Minnie Mouse\",\"idade\":25,\"id\":1004,\"risco\":0}}";
        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(respostaEsperada));
    }

    //put
    @Test
    @DisplayName("Autaliza um clienta já previamente cadastrado")
    public void atualizaCliente() {

        String cadastroRealizado = "{\n" +
                "  \"id\": 1005,\n" +
                "  \"idade\": 15,\n" +
                "  \"nome\": \"Pluto\",\n" +
                "  \"risco\": 0\n" +
                "}";
        String respostaEsperada = "{\"1005\":{\"nome\":\"Pluto\",\"idade\":15,\"id\":1005,\"risco\":0}}";
    }

    //delete
    @Test@DisplayName("Deleta um cliente por id")
    public void deletaCliente() {
        String enderecoApiCliente = "http://localhost:8080/";
        String endpointCliente = "cliente/1005";


    }


}
