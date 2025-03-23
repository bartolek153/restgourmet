
export function snakeCaseToHumanReadable(snakeCase: string | null | undefined): string {
  if (!snakeCase) {
    return "";
  }

  const words = snakeCase.toLowerCase().split("_");
  return words.map((word) => (word ? word.charAt(0).toUpperCase() + word.slice(1) : "")).join(" ");
}
