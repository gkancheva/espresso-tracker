import { Card, CardActionArea, CardContent, CardMedia } from "@mui/material";
import { EspressoSetting } from "../models/EspressoSetting";
import Typography from "@mui/material/Typography";
import { formatDate } from "../utils/DateUtils";

interface EspressoSettingDetailsProps {
  espressoSetting: EspressoSetting;
}

export const EspressoSettingDetails = ({ espressoSetting }: EspressoSettingDetailsProps): JSX.Element => {
  return (
    <Card sx={{ maxWidth: 500 }}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image="/static/images/cards/contemplative-reptile.jpg"
          alt="green iguana"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {espressoSetting.coffee.name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {espressoSetting.coffee.bakery} {formatDate(espressoSetting.coffee.roastedOnDate)}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  )
}