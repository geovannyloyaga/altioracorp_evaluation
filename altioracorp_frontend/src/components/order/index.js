/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import moment from "moment";
import { useEffect, useRef, useState } from "react";
import { Button, Spinner, Table } from "react-bootstrap";
import { useLocation } from "react-router-dom";
import { toast } from "react-toastify";
import ModalForm from "./modalForm";
import ArticlesForm from "./articlesForm";

const Order = () => {
  const location = useLocation();
  const client = location?.state?.client;
  const [ordersData, setOrdersData] = useState({ loading: false, data: [] });
  const [formData, setFormData] = useState({ open: false, data: {} });
  const [articlesForm, setArticlesForm] = useState({ open: false, data: {} });
  const mounted = useRef(false);
  const currency = "USD";

  function getOrdersByClient() {
    setOrdersData({ ...ordersData, loading: true });
    axios
      .get(`http://localhost:8081/api/orders/getOrderList`)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          setOrdersData({ loading: false, data: responseApi.responseList });
          return;
        }
        setOrdersData({ loading: false, data: [] });
        toast("No se encontró ordenes registradas");
      })
      .catch(function (error) {
        // handle error
        setOrdersData({ loading: false, data: [] });
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  }

  useEffect(() => {
    if (!mounted.current) {
      getOrdersByClient();
      mounted.current = true;
    }

    return () => {};
  }, []);

  return (
    <>
      <div className="row mb-2">
        <div className="col-9">
          <h5>Lista de ordenes</h5>
        </div>
        <div className="col-3" style={{ textAlign: "right" }}>
          <Button
            onClick={() => getOrdersByClient()}
            variant="outline-secondary"
            style={{ marginRight: 8 }}
          >
            Actualizar
          </Button>
          <Button
            variant="outline-primary"
            onClick={() =>
              setFormData({
                open: true,
                data: {
                  articles: [],
                  subtotal: 0,
                  date: moment().format("YYYY/MM/DD"),
                  client: client,
                },
              })
            }
          >
            Crear orden
          </Button>
        </div>
      </div>
      <div>
        {ordersData.loading ? (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              height: "calc(100vh - 200px)",
            }}
          >
            <Spinner animation="grow" style={{ alignSelf: "center" }} />
          </div>
        ) : (
          <Table responsive striped bordered hover>
            <thead>
              <tr>
                <th>ID ORDEN</th>
                <th>CÓDIGO</th>
                <th>CLIENTE</th>
                <th>DATE</th>
                <th>SUBTOTAL</th>
                <th># ARTÍCULOS</th>
                <th>CREADO</th>
              </tr>
            </thead>
            <tbody>
              {ordersData.data?.length > 0 &&
                ordersData.data.map((order) => (
                  <tr>
                    <td>{order?.id}</td>
                    <td>{order?.code}</td>
                    <td>{`${order?.client?.name} ${order?.client?.lastname}`}</td>
                    <td>{order?.date}</td>
                    <td>
                      {order?.subtotal.toLocaleString("en-US", {
                        style: "currency",
                        currency: currency,
                      })}
                    </td>
                    <td>
                      <Button
                        onClick={() => {
                          setArticlesForm({
                            open: true,
                            data: order?.articleOrders ?? [],
                          });
                        }}
                      >
                        {order?.articleOrders?.length ?? 0}
                      </Button>
                    </td>
                    <td>
                      {moment(order?.createdAt).format("YYYY-MM-DD HH:mm:ss")}
                    </td>
                  </tr>
                ))}
            </tbody>
          </Table>
        )}
      </div>
      {formData.open && (
        <ModalForm
          openModal={formData.open}
          orderForm={formData.data}
          closeModal={(reload = false) => {
            setFormData({ open: false, data: {} });
            if (reload) {
              getOrdersByClient();
            }
          }}
        />
      )}
      {articlesForm.open && (
        <ArticlesForm
          openModal={articlesForm.open}
          articles={articlesForm.data}
          closeModal={() => {
            setArticlesForm({ open: false, data: {} });
          }}
        />
      )}
    </>
  );
};

export default Order;
