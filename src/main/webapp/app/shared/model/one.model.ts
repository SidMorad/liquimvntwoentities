export interface IOne {
  id?: number;
  fieldonefirst?: string;
  fieldtwofirst?: string;
}

export class One implements IOne {
  constructor(public id?: number, public fieldonefirst?: string, public fieldtwofirst?: string) {}
}
