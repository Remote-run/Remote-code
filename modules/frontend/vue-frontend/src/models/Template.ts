export class Template {
  private _templateKey: string;
  private _templateName: string;
  private _gitCloneRepo: string;
  private _templateLink: string;

  constructor (templateKey: string, templateName: string, gitCloneRepo: string, templateLink: string) {
    this._templateKey = templateKey
    this._templateName = templateName
    this._gitCloneRepo = gitCloneRepo
    this._templateLink = templateLink
  }

  get templateKey (): string {
    return this._templateKey
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
