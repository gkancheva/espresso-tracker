import PhoneIcon from '@mui/icons-material/Phone';
import { Card, CardActions, CardContent, CardMedia } from "@mui/material";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { Bakery } from "../models/Bakery";
import { useNavigate } from "react-router";

interface Props {
  bakery: Bakery;
  hideLearnMore?: boolean;
  children?: React.ReactNode;
}

export const BakeryCard = ({ bakery, hideLearnMore, children }: Props) => {
  const navigate = useNavigate();

  return (
    <Card>
      <CardMedia
        component="img"
        alt={bakery.name}
        height="140"
        src={bakery.imgSrc ? bakery.imgSrc : '/espresso-shot.jpg'}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {bakery.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {bakery.address}
        </Typography>
        <Typography
          variant="body2"
          color="text.secondary"
          mt={1}
          style={{ alignItems: 'center', display: 'flex' }}>
          <PhoneIcon fontSize='small' style={{ margin: 2 }} /> {bakery.phoneNumber}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" href={bakery.webSite} target='_blank'>Visit online</Button>
        {
          !hideLearnMore &&
          <Button size="small" onClick={() => navigate(`/bakeries/${bakery.id}`)}>
            Learn More
          </Button>
        }
      </CardActions>
      {children}
    </Card>
  );

}