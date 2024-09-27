// ** React Imports
import { Fragment, useState, FC } from 'react'

// ** Third Party Components
import classnames from 'classnames'
import { Eye, EyeOff } from 'react-feather'
import { InputGroup, Input, InputGroupText, Label } from 'reactstrap'

// ** Types for Props
interface InputPasswordToggleProps {
    hideIcon?: React.ReactNode
    showIcon?: React.ReactNode
    visible?: boolean
    className?: string
    placeholder?: string
    iconSize?: number
    inputClassName?: string
    label?: string
    htmlFor?: string
    onChange?: any
    [key: string]: any // For allowing other props like `register` to be passed
}

const InputPasswordToggle: FC<InputPasswordToggleProps> = (props) => {
    // ** Destructure Props
    const {
        label,
        hideIcon,
        showIcon,
        visible = false,
        className,
        htmlFor,
        placeholder = '············',
        iconSize = 14,
        inputClassName,
        onChange,
        ...rest
    } = props

    // ** State
    const [inputVisibility, setInputVisibility] = useState(visible)

    // ** Renders Icon Based On Visibility
    const renderIcon = () => {
        return !inputVisibility
            ? hideIcon ?? <Eye size={iconSize} />
            : showIcon ?? <EyeOff size={iconSize} />
    }

    return (
        <Fragment>
            {label && htmlFor ? <Label for={htmlFor}>{label}</Label> : null}
            <InputGroup className={classnames(className)}>
                <Input
                    type={!inputVisibility ? 'password' : 'text'}
                    placeholder={placeholder}
                    className={classnames(inputClassName)}
                    id={htmlFor}
                    onChange={props.onChange}
                    {...rest} // Spread the rest of the props like `register`
                />
                <InputGroupText className="cursor-pointer" onClick={() => setInputVisibility(!inputVisibility)}>
                    {renderIcon()}
                </InputGroupText>
            </InputGroup>
        </Fragment>
    )
}

export default InputPasswordToggle
