export interface IOne {
  id?: number;
  fieldonefirst?: string;
}

export class One implements IOne {
  constructor(public id?: number, public fieldonefirst?: string) {}
}
