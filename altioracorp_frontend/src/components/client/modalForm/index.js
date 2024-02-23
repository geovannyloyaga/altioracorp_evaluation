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

const ModalForm = ({ openModal, closeModal, clientForm }) => {
  const [client, setClient] = useState(clientForm);

  const handleChangeText = (event) => {
    setClient({ ...client, [event.target.name]: event.target.value });
  };

  const onSave = () => {
    axios
      .post(`http://localhost:8081/api/clients/save`, client)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          alert("El cliente fue creado");
          closeModal(true);
          return;
        }
        toast("No se pudo crear el cliente");
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
      .put(`http://localhost:8081/api/clients/update`, client)
      .then(function (response) {
        let responseApi = response.data;
        if (responseApi.code === 200) {
          alert("El cliente fue actualizado");
          closeModal(true);
          return;
        }
        toast("No se pudo actualizar el cliente");
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
          Crear cliente
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="row">
          <div className="col-12">
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Dni</FormLabel>
              <FormControl
                type="text"
                name="dni"
                maxLength={13}
                value={client.dni}
                defaultValue={client.dni}
                onChange={handleChangeText.bind(this)}
                placeholder="Ingrese el nombre"
              />
            </FormGroup>
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Nombre</FormLabel>
              <FormControl
                type="text"
                name="name"
                maxLength={100}
                value={client.name}
                defaultValue={client.name}
                onChange={handleChangeText.bind(this)}
                placeholder="Ingrese el nombre"
              />
            </FormGroup>
            <FormGroup style={{ display: "grid" }}>
              <FormLabel>Apellido</FormLabel>
              <FormControl
                type="text"
                name="lastName"
                maxLength={100}
                value={client.lastName}
                defaultValue={client.lastName}
                onChange={handleChangeText.bind(this)}
                placeholder="Ingrese el nombre"
              />
            </FormGroup>
          </div>

          <div className="col-12 mb-3 mt-3" style={{ textAlign: "center" }}>
            {
              <Button
                variant="success"
                onClick={() => {
                  client?.id ? onUpdate() : onSave();
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
