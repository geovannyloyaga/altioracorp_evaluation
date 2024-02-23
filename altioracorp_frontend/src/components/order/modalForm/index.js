import axios from "axios";
import moment from "moment";
import { useEffect, useState } from "react";
import {
  Button,
  FormControl,
  FormGroup,
  FormLabel,
  Modal,
  Table,
} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import DatePicker from "react-multi-date-picker";
import { toast } from "react-toastify";

const ModalForm = ({ openModal, closeModal, orderForm }) => {
  const [order, setOrder] = useState(orderForm);
  const [articleList, setArticleList] = useState([]);
  const [clientList, setClientList] = useState([]);
  const [article, setArticle] = useState({ unitPrice: 0 });
  const currency = "USD";

  const handleChangeTextArticle = (event) => {
    setArticle({ ...article, [event.target.name]: event.target.value });
  };

  const onAddArticle = () => {
    if (!article?.id || article?.id === "") {
      alert("Es necesario seleccionar artículo");
      return;
    }
    if (!article?.quantity || article?.quantity === "") {
      alert("Es necesario seleccionar artículo");
      return;
    }
    let foundArticle = articleList.find(
      (articleItem) => articleItem?.id === +article.id
    );
    foundArticle.quantity = +article?.quantity;
    let newOrder = { ...order };
    newOrder.articles.push(foundArticle);
    newOrder.subtotal += foundArticle.unitPrice * foundArticle?.quantity;
    setOrder(newOrder);
    setArticle({ quantity: "", id: "" });
  };

  function getClients() {
    axios
      .get("http://localhost:8081/api/clients/getClientList")
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          setClientList(responseApi.responseList);
          return;
        }
        setClientList([]);
        toast("No se encontró clientes registrados");
      })
      .catch(function (error) {
        // handle error
        setClientList([]);
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  }

  const onRemoveArticle = (index) => {
    let newOrder = { ...order };
    let foundArticle = articleList[index];
    let newArticles = [...newOrder.articles];
    newArticles.splice(index, 1);
    newOrder.articles = newArticles;
    newOrder.subtotal -= foundArticle.unitPrice * foundArticle?.quantity;
    setOrder(newOrder);
    setArticle({ quantity: "", id: "" });
  };

  const onSave = () => {
    order.date = moment(order.date);
    axios
      .post(`http://localhost:8081/api/orders/save`, order)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          alert("La orden fue creada");
          closeModal(true);
          return;
        }
        toast("No se pudo crear la orden");
      })
      .catch(function (error) {
        // handle error
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  };

  function getArticles() {
    setArticleList([]);
    axios
      .get("http://localhost:8081/api/articles/getArticleList")
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          setArticleList(responseApi.responseList);
          return;
        }
        setArticleList([]);
        toast("No se encontró artículos registrados");
      })
      .catch(function (error) {
        // handle error
        setArticleList([]);
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  }

  useEffect(() => {
    getArticles();
    getClients();
    return () => {};
  }, []);

  return (
    <Modal
      size="lg"
      show={openModal}
      onHide={() => closeModal()}
      aria-labelledby="example-modal-sizes-title-lg"
    >
      <Modal.Header closeButton>
        <Modal.Title id="example-modal-sizes-title-lg">Crear orden</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="row">
          <div className="col-6">
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Fecha</FormLabel>
              <DatePicker
                value={order?.date || ""}
                onChange={(date) => {
                  setOrder({ ...order, date: date?.isValid ? date : "" });
                }}
                format="YYYY/MM/DD"
                style={{ height: 35 }}
              />
            </FormGroup>
            <FormGroup controlId="formBasicEmail">
              <FormLabel>Cliente</FormLabel>
              <Form.Select
                name="client_id"
                value={order?.client_id}
                defaultValue={article?.client_id}
                aria-label="Default select example"
                onChange={(event) => {
                  setOrder({ ...order, client_id: event.target.value });
                }}
              >
                <option>Seleccione cliente</option>
                {clientList.map((client) => (
                  <option
                    value={client?.id}
                  >{`${client?.dni} | ${client?.name} ${client?.lastname}`}</option>
                ))}
              </Form.Select>
            </FormGroup>
          </div>
          <div className="col-6">
            <FormGroup style={{ display: "grid", textAlign: "right" }}>
              <FormLabel>Subtotal</FormLabel>
              <h5>
                {(order?.subtotal ?? 0).toLocaleString("en-US", {
                  style: "currency",
                  currency: currency,
                })}
              </h5>
            </FormGroup>
          </div>

          {!order?.id && (
            <div className="col-12 mt-3" style={{ display: "inline" }}>
              <div className="row">
                <FormGroup className="mb-3 col-6" controlId="formBasicEmail">
                  <FormLabel>Código</FormLabel>
                  <Form.Select
                    name="id"
                    value={article?.id}
                    defaultValue={article?.id}
                    aria-label="Default select example"
                    onChange={handleChangeTextArticle.bind(this)}
                  >
                    <option>Seleccione artículo</option>
                    {articleList
                      .filter(
                        (articleItem) => !order.articles.includes(articleItem)
                      )
                      .map((article) => (
                        <option
                          value={article?.id}
                        >{`${article?.code} | ${article?.name}`}</option>
                      ))}
                  </Form.Select>
                </FormGroup>
                <FormGroup className="mb-3 col-3" controlId="formBasicEmail">
                  <FormLabel>Cantidad</FormLabel>
                  <FormControl
                    type="number"
                    name="quantity"
                    value={article.quantity}
                    defaultValue={article.quantity}
                    onChange={handleChangeTextArticle.bind(this)}
                    placeholder="Cantidad"
                  />
                </FormGroup>
                <FormGroup
                  className="mb-3 col-3"
                  style={{ textAlign: "right", marginTop: 24 }}
                  controlId="formBasicEmail"
                >
                  <Button
                    variant="outline-primary"
                    onClick={onAddArticle.bind(this)}
                  >
                    Agregar
                  </Button>
                </FormGroup>
              </div>
            </div>
          )}

          <div className="col-12 mt-3">
            <Table responsive striped bordered hover>
              <thead>
                <tr>
                  {!order?.id ? <th>ELIMINAR</th> : <th>ID</th>}
                  <th>CÓDIGO</th>
                  <th>NOMBRE</th>
                  <th>CANTIDAD</th>
                  <th>PRECIO UNITARIO</th>
                </tr>
              </thead>
              <tbody>
                {order?.articles &&
                  order.articles.map((article, index) => (
                    <tr>
                      <td>
                        {!order?.id ? (
                          <Button
                            variant="outline-secondary"
                            onClick={() => {
                              onRemoveArticle(index);
                            }}
                          >
                            Eliminar
                          </Button>
                        ) : (
                          article.id
                        )}
                      </td>
                      <td>{article?.code}</td>
                      <td>{article?.name}</td>
                      <td>{article?.quantity}</td>
                      <td>
                        {(article?.unitPrice).toLocaleString("en-US", {
                          style: "currency",
                          currency: currency,
                        })}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          </div>

          <div className="col-12 mt-3" style={{ textAlign: "center" }}>
            {!order?.id && (
              <Button variant="success" onClick={onSave.bind(this)}>
                Guardar
              </Button>
            )}
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default ModalForm;
