import { PureAbility } from '@casl/ability'
import { initialAbility } from './initialAbility'

const userDataString = localStorage.getItem('userData') || '{}';
const userData = JSON.parse(userDataString);
const existingAbility = userData ? userData.ability : null

export default new PureAbility(existingAbility || initialAbility);
