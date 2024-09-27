// ** React Imports
import React, { useState, createContext, ReactNode } from 'react'

// ** Intl Provider Import
import { IntlProvider } from 'react-intl'

// ** Core Language Data
import messagesEn from '../../assets/data/locales/en.json'
import messagesDe from '../../assets/data/locales/de.json'
import messagesFr from '../../assets/data/locales/fr.json'
import messagesPt from '../../assets/data/locales/pt.json'

// ** User Language Data
import userMessagesEn from '../../assets/data/locales/en.json'
import userMessagesDe from '../../assets/data/locales/de.json'
import userMessagesFr from '../../assets/data/locales/fr.json'
import userMessagesPt from '../../assets/data/locales/pt.json'

// ** Menu msg obj
const menuMessages: { [key: string]: { [key: string]: string } } = {
  en: { ...messagesEn, ...userMessagesEn },
  de: { ...messagesDe, ...userMessagesDe },
  fr: { ...messagesFr, ...userMessagesFr },
  pt: { ...messagesPt, ...userMessagesPt }
}

// ** Context Types
interface IntlContextProps {
  locale: string
  switchLanguage: (lang: string) => void
}

// ** Create Context
const IntlContext = createContext<IntlContextProps | undefined>(undefined)

interface IntlProviderWrapperProps {
  children: ReactNode
}

const IntlProviderWrapper: React.FC<IntlProviderWrapperProps> = ({ children }) => {
  // ** States
  const [locale, setLocale] = useState<string>('en')
  const [messages, setMessages] = useState<{ [key: string]: string }>(menuMessages['en'])

  // ** Switches Language
  const switchLanguage = (lang: string) => {
    setLocale(lang)
    setMessages(menuMessages[lang])
  }

  return (
      <IntlContext.Provider value={{ locale, switchLanguage }}>
        <IntlProvider key={locale} locale={locale} messages={messages} defaultLocale='en'>
          {children}
        </IntlProvider>
      </IntlContext.Provider>
  )
}

export { IntlProviderWrapper, IntlContext }
