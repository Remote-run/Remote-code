export class Template {
  private _Id: number;
  private _templateName: string;
  private _gitCloneRepo: string;
  private _templateLink: string;

  constructor (Id: number, templateName: string, gitCloneRepo: string, templateLink: string) {
    this._Id = Id
    this._templateName = templateName
    this._gitCloneRepo = gitCloneRepo
    this._templateLink = templateLink
  }

  get Id (): number {
    return this._Id
  }

  get templateName (): string {
    return this._templateName
  }

  get gitCloneRepo (): string {
    return this._gitCloneRepo
  }

  get templateLink (): string {
    return this._templateLink
  }
}
