// ** Imports createContext function
import { createContext } from 'react'

// ** Imports createContextualCan function
import { createContextualCan } from '@casl/react'
import { AnyAbility, PureAbility } from '@casl/ability'

// ** Define default ability (an empty ability)
const defaultAbility = new PureAbility()

// ** Define the type for AbilityContext
export const AbilityContext = createContext<AnyAbility>(defaultAbility) // Default to an empty ability

// ** Init Can Context
export const Can = createContextualCan(AbilityContext.Consumer)
