import React, { useEffect, useState, useRef, useCallback } from "react";
import DesignationForm from "../components/designation/DesignationForm";
import DesignationTable from "../components/designation/DesignationTable";
import Toast from "../components/common/Toast";
import {
  getDesignation,
  createDesignation,
  updateDesignation,
  deleteDesignation,
} from "../api/designationApi";
import { useReactToPrint } from "react-to-print";

const DesignationMaster = () => {
  const [designations, setDesignations] = useState([]);
  const [printData, setPrintData] = useState([]);
  const [toast, setToast] = useState(null);
  const [editData, setEditData] = useState(null);
  const [isPrinting, setIsPrinting] = useState(false);
  const [pendingPrint, setPendingPrint] = useState(false);

  const tableRef = useRef(null);

  // Toast helper
  const showToast = (message, type = "success") => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  // Load designations
  const loadDesignation = () => {
    getDesignation()
      .then((res) => setDesignations(res.data))
      .catch(() => showToast("Failed to load designations", "danger"));
  };

  // Save or update designation
  const handleSave = (data) => {
    const apiCall = editData
      ? updateDesignation(editData.id, data)
      : createDesignation(data);

    apiCall
      .then(() => {
        showToast(`Designation ${editData ? "updated" : "created"} successfully`);
        setEditData(null);
        loadDesignation();
      })
      .catch((err) => {
        if (err.response?.status === 409) {
          showToast(err.response.data, "danger");
        } else {
          showToast("Error occurred", "danger");
        }
      });
  };

  // Edit handler
  const handleEdit = (designation) => {
    setEditData(designation);
  };

  // Delete handler
  const handleDelete = (id) => {
    if (window.confirm("Are you sure?")) {
      deleteDesignation(id)
        .then(() => {
          showToast("Designation deleted");
          loadDesignation();
        })
        .catch(() => showToast("Delete failed", "danger"));
    }
  };

  // Load data on mount
  useEffect(() => {
    loadDesignation();
  }, []);

  // react-to-print handler - Correct implementation for v3.1.0
  const handlePrint = useReactToPrint({
    contentRef: tableRef,
    documentTitle: "Designation Master Report",
    onAfterPrint: () => {
      setIsPrinting(false);
      showToast("PDF generated!", "success");
    },
    onBeforePrint: () => {
      setIsPrinting(true);
      return Promise.resolve();
    }
  });

  // Trigger print
  const handlePrintClick = async () => {
    showToast("Preparing PDF...", "info");
    try {
      const res = await getDesignation();
      setPrintData(res.data);
      setPendingPrint(true);
    } catch (err) {
      showToast("Failed to fetch data", "danger");
    }
  };

  // Print when data and DOM are ready
  useEffect(() => {
    if (pendingPrint) {
      // Delay a tiny bit to let React re-render
      setTimeout(() => {
        if (
          tableRef.current &&
          tableRef.current.innerHTML.trim() !== "" &&
          printData.length > 0
        ) {
          // For v3.1.0, just call the function directly
          handlePrint();
        } else {
          showToast("Nothing to print", "danger");
          setIsPrinting(false);
        }
        setPendingPrint(false);
      }, 1000); // 1000ms delay
    }
  }, [pendingPrint, printData]);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow position-relative">
      {/* Header */}
      <h2 className="text-info mb-4 d-flex justify-content-between align-items-center">
        Designation Type Master
        <button
          className="btn btn-outline-primary btn-sm"
          onClick={handlePrintClick}
          disabled={isPrinting}
        >
          {isPrinting ? "Preparing PDF..." : "Download PDF"}
        </button>
      </h2>

      {/* Form */}
      <DesignationForm onSave={handleSave} editData={editData} />

      <hr className="my-4" />

      {/* Table */}
      <h4 className="text-secondary mb-3">All Designation Types</h4>
      <DesignationTable
        data={designations}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />

      {/* Hidden table for PDF */}
      <div style={{ position: "fixed", top: "-10000px", left: "-10000px" }}>
        <DesignationTable
          ref={tableRef}
          data={printData}
          onEdit={() => {}}
          onDelete={() => {}}
        />
      </div>

      {/* Toast */}
      <div className="toast-container position-fixed top-0 end-0 p-3">
        {toast && (
          <Toast
            message={toast.message}
            type={toast.type}
            onClose={() => setToast(null)}
          />
        )}
      </div>
    </div>
  );
};

export default DesignationMaster;