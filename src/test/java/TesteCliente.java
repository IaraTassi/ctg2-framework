import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente = "http://localhost:8080/";
    String endpointCliente = "cliente";
    String endpointApagaTodos = "/apagaTodos";
    String listavazia = "{}";

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
                .statusCode(HttpStatus.SC_OK)
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
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat().body(containsString(respostaEsperada));
    }

    //put
    @Test
    @DisplayName("Quando eu atualizar um cliente, então ele deve ser atualizado")
    public void atualizaCliente() {

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 25,\n" +
                "  \"nome\": \"Minnie Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String clienteAtualizado = "{\n" +
                "  \"id\": 1004,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Minnie Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"1004\":{\"nome\":\"Minnie Mouse\",\"idade\":33,\"id\":1004,\"risco\":0}}";

    // tinha esquecido
        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)
        .when()
                .put(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(containsString(respostaEsperada));

    }

    //delete
      @Test
      @DisplayName("Quando deleta um cliente, então ele deve ser removido com sucesso")
      public void deletaCliente() {

          String clienteParaCadastrar = "{\n" +
                  "  \"id\": 1004,\n" +
                  "  \"idade\": 33,\n" +
                  "  \"nome\": \"Minnie Mouse\",\n" +
                  "  \"risco\": 0\n" +
                  "}";
          String respostaEsperada = "Cliente removido: { NOME: Minnie Mouse, IDADE: 33, ID: 1004 }";

          given()
                  .contentType(ContentType.JSON)
                  .body(clienteParaCadastrar)
                  .when()
                  .post(enderecoApiCliente + endpointCliente)
                  .then()
                  .statusCode(HttpStatus.SC_CREATED);

          given()
                  .contentType(ContentType.JSON)
                  .when()
                  .delete(enderecoApiCliente + endpointCliente + "/1004")
                  .then()
                  .statusCode(HttpStatus.SC_OK)
                  .body(new IsEqual<>(respostaEsperada));

      }
    }
