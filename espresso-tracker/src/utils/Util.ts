
export const INPUT_TXT_MIN_LENGTH = 3;
export const INPUT_TXT_MAX_LENGTH = 255;

export const checkInputIsValid = (str: string, max?: number): boolean => {
  const minLengthRespected = str ? str.trim().length > INPUT_TXT_MIN_LENGTH : false;
  const maxLengthRespected = (max && str.trim().length <= max) || (str.trim().length < INPUT_TXT_MAX_LENGTH);
  return minLengthRespected && maxLengthRespected;
}

const NUMBER_FORMATTER = Intl.NumberFormat(
  'en-us', {maximumFractionDigits: 2});

export const checkNumberInput = (num: number, min: number, max: number): boolean =>
  !num ? false : num >= min && num <= max;

export const formatNumber = (num?: number) => {
  if (num == null) {
    return 'N/A';
  }
  return NUMBER_FORMATTER.format(num);
}