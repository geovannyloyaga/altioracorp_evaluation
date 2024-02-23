/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import moment from "moment";
import React, { useEffect, useRef, useState } from "react";
import { Button, Spinner, Table } from "react-bootstrap";
import { toast } from "react-toastify";
import ModalForm from "./modalForm";

export const Article = () => {
  const [articlesData, setArticlesData] = useState({
    loading: false,
    data: [],
  });
  const [formData, setFormData] = useState({ open: false, data: {} });
  const mounted = useRef(false);

  function getArticles() {
    setArticlesData({ ...articlesData, loading: true });
    axios
      .get("http://localhost:8081/api/articles/getArticleList")
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          setArticlesData({ loading: false, data: responseApi.responseList });
          return;
        }
        setArticlesData({ loading: false, data: [] });
        toast("No se encontró artículos registrados");
      })
      .catch(function (error) {
        // handle error
        setArticlesData({ loading: false, data: [] });
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  }

  function deleteArticle(article) {
    setArticlesData({ ...articlesData, loading: true });
    axios
      .delete(`http://localhost:8081/api/articles/delete/${article?.id}`)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          toast("Artículo eliminado");
          getArticles();
          return;
        }
        toast(responseApi.error ?? "");
      })
      .catch(function (error) {
        // handle error
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  }

  useEffect(() => {
    if (!mounted.current) {
      getArticles();
      mounted.current = true;
    }

    return () => {};
  }, []);

  return (
    <>
      <div className="row mb-2">
        <div className="col-9">
          <h5>Lista de artículos</h5>
        </div>
        <div className="col-3" style={{ textAlign: "right" }}>
          <Button
            onClick={() => {
              getArticles();
            }}
            variant="outline-secondary"
            style={{ marginRight: 8 }}
          >
            Actualizar
          </Button>
          <Button
            variant="outline-primary"
            onClick={() => setFormData({ open: true, data: {} })}
          >
            Crear artículo
          </Button>
        </div>
      </div>
      <div>
        {articlesData.loading ? (
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
                <th>ACCIONES</th>
                <th>ID ARTÍCULO</th>
                <th>CÓDIGO</th>
                <th>NOMBRE</th>
                <th>PRECIO UNITARIO</th>
                <th>CREADO</th>
              </tr>
            </thead>
            <tbody>
              {articlesData.data?.length > 0 &&
                articlesData.data.map((article) => (
                  <tr>
                    <td>
                      <span
                        onClick={() =>
                          setFormData({ open: true, data: article })
                        }
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          strokeWidth={1.5}
                          stroke="currentColor"
                          className="w-6 h-6 mx-2 text-primary"
                          width={24}
                          height={24}
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
                          />
                        </svg>
                      </span>
                      <span onClick={() => deleteArticle(article)}>
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="1.5"
                          stroke="currentColor"
                          className="w-6 h-6 mx-2 text-danger"
                          width={24}
                          height={24}
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                          />
                        </svg>
                      </span>
                    </td>
                    <td>{article?.id}</td>
                    <td>{article?.code}</td>
                    <td>{article?.name}</td>
                    <td>{article?.unitPrice}</td>
                    <td>
                      {moment(article?.createdAt).format("YYYY-MM-DD HH:mm:ss")}
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
          articleForm={formData.data}
          closeModal={(reload = false) => {
            setFormData({ open: false, data: {} });
            if (reload) {
              getArticles();
            }
          }}
        />
      )}
    </>
  );
};
