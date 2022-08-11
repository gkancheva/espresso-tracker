import {useParams} from "react-router";

export const BakeryDetails = () => {
  const { id } = useParams();

  return (
    <>Bakery id: {id}</>
  );
}