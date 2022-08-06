import dayjs, { Dayjs } from "dayjs";

const DEFAULT_FORMAT_DATE = 'D MMM YYYY';

export const parse = (date: string): Dayjs => dayjs(date);

export const formatDate = (date: Dayjs, format?: string): string => {
  return date.format(format ? format : DEFAULT_FORMAT_DATE);
}