export class RegisterUser {
	constructor(
		public id?: number,
		public email?: string,
		public password?: string,
		public matchingPassword?: string
	){}
}