import { Modal, Table } from "react-bootstrap";

const ArticlesForm = ({ openModal, closeModal, articles }) => {
  const currency = "USD";
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
          <div className="col-12 mt-3">
            <Table responsive striped bordered hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>CÓDIGO</th>
                  <th>NOMBRE</th>
                  <th>CANTIDAD</th>
                  <th>PRECIO UNITARIO</th>
                </tr>
              </thead>
              <tbody>
                {articles &&
                  articles.map((articleOrder, index) => (
                    <tr>
                      <td>{articleOrder?.article?.id}</td>
                      <td>{articleOrder?.article?.code}</td>
                      <td>{articleOrder?.article?.name}</td>
                      <td>{articleOrder?.quantity}</td>
                      <td>
                        {(articleOrder?.article?.unitPrice).toLocaleString(
                          "en-US",
                          {
                            style: "currency",
                            currency: currency,
                          }
                        )}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default ArticlesForm;
