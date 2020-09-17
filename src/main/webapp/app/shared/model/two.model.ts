export interface ITwo {
  id?: number;
  fieldtwofirst?: string;
}

export class Two implements ITwo {
  constructor(public id?: number, public fieldtwofirst?: string) {}
}
