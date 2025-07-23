import { useEffect, useState } from "react";
import { fetchLodgingTypes } from "../features/loadingTypeService";

function LodgingForm() {
  const [types, setTypes] = useState([]);

  useEffect(() => {
    fetchLodgingTypes()
      .then(setTypes)
      .catch((err) => console.error("Error al cargar tipos:", err));
  }, []);

  return (
    <select name="typeId">
      {types.map((type) => (
        <option key={type.id} value={type.id}>
          {type.name}
        </option>
      ))}
    </select>
  );
}

export default LodgingForm;
