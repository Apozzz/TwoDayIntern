import { Moment } from "moment";

export function formatDateForReport(date: Moment): string {
    const year = date.year();
    const month = String(date.month() + 1).padStart(2, '0');
    const day = String(date.date()).padStart(2, '0');
    const hour = String(date.hour()).padStart(2, '0');
    const minute = String(date.minute()).padStart(2, '0');
    return `${year}${month}${day}-${hour}${minute}`;
}