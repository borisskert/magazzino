export interface Customer {
  id: number;
  firstName: string;
  lastName: string;
}

export function emptyCustomer(): Customer {
  return {
    id: 0,
    firstName: '',
    lastName: ''
  };
}
