import axios from "axios";
import { useState } from "react";
import {
  Button,
  FormControl,
  FormGroup,
  FormLabel,
  Modal,
} from "react-bootstrap";
import { toast } from "react-toastify";

const ModalForm = ({ openModal, closeModal, articleForm }) => {
  const [article, setArticle] = useState(articleForm);

  const handleChangeText = (event) => {
    setArticle({ ...article, [event.target.name]: event.target.value });
  };

  const onSave = () => {
    axios
      .post(`http://localhost:8081/api/articles/save`, article)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          alert("El artículo fue creado");
          closeModal(true);
          return;
        }
        toast("No se pudo crear el artículo");
      })
      .catch(function (error) {
        // handle error
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  };

  const onUpdate = () => {
    axios
      .put(`http://localhost:8081/api/articles/update`, article)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          alert("El artículo fue actualizado");
          closeModal(true);
          return;
        }
        toast("No se pudo actualizar el artículo");
      })
      .catch(function (error) {
        // handle error
        toast("Hubo inconveniente en el sistema");
      })
      .finally(function () {
        // always executed
      });
  };

  return (
    <Modal
      size="md"
      show={openModal}
      onHide={() => closeModal()}
      aria-labelledby="example-modal-sizes-title-lg"
    >
      <Modal.Header closeButton>
        <Modal.Title id="example-modal-sizes-title-lg">
          Crear artículo
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="row">
          <div className="col-12">
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Nombre</FormLabel>
              <FormControl
                type="text"
                name="name"
                maxLength={100}
                value={article.name}
                defaultValue={article.name}
                onChange={handleChangeText.bind(this)}
                placeholder="Ingrese el nombre"
              />
            </FormGroup>
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Precio unitario</FormLabel>
              <FormControl
                type="number"
                name="unitPrice"
                maxLength={100}
                value={article.unitPrice}
                defaultValue={article.unitPrice}
                onChange={handleChangeText.bind(this)}
                placeholder="$ XXXX.XX"
              />
            </FormGroup>
          </div>

          <div className="col-12 mb-3 mt-3" style={{ textAlign: "center" }}>
            {
              <Button
                variant="success"
                onClick={() => {
                  article?.id ? onUpdate() : onSave();
                }}
              >
                Guardar
              </Button>
            }
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default ModalForm;
