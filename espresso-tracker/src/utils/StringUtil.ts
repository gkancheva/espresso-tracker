
export const INPUT_TXT_MIN_LENGTH = 3;
export const INPUT_TXT_MAX_LENGTH = 255;

export const checkInput = (str: string, max?: number): boolean => {
  const minLengthRespected = str ? str.trim().length > INPUT_TXT_MIN_LENGTH : false;
  const maxLengthRespected = (max && str.trim().length <= max) || (str.trim().length < INPUT_TXT_MAX_LENGTH);
  return minLengthRespected && maxLengthRespected;
}
