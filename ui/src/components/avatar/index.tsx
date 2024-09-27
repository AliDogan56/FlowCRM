// ** React Imports
import { forwardRef, ReactNode, CSSProperties } from 'react'

// ** Third Party Components
import { Badge } from 'reactstrap'
import classnames from 'classnames'

// ** Types
interface AvatarProps {
  color?: string
  className?: string
  imgClassName?: string
  initials?: boolean
  size?: 'sm' | 'lg' | 'xl'
  badgeUp?: boolean
  content?: string
  icon?: ReactNode
  badgeColor?: string
  badgeText?: string
  img?: string | boolean
  imgHeight?: string | number
  imgWidth?: string | number
  status?: 'online' | 'offline' | 'away' | 'busy'
  tag?: keyof JSX.IntrinsicElements
  contentStyles?: CSSProperties
  [key: string]: any
}

const Avatar = forwardRef<HTMLDivElement, AvatarProps>((props, ref) => {
  // ** Props
  const {
    color,
    className,
    imgClassName,
    initials,
    size,
    badgeUp,
    content,
    icon,
    badgeColor,
    badgeText,
    img,
    imgHeight,
    imgWidth,
    status,
    tag: Tag = 'div',
    contentStyles,
    ...rest
  } = props

  // ** Function to extract initials from content
  const getInitials = (str: string) => {
    const results: string[] = []
    const wordArray = str.split(' ')
    wordArray.forEach(e => {
      results.push(e[0])
    })
    return results.join('')
  }

  return (
      <Tag
          className={classnames('avatar', {
            [className as string]: className,
            [`bg-${color}`]: color,
            [`avatar-${size}`]: size
          })}
          ref={ref}
          {...rest}
      >
        {img === false || img === undefined ? (
            <span
                className={classnames('avatar-content', {
                  'position-relative': badgeUp
                })}
                style={contentStyles}
            >
          {initials ? getInitials(content as string) : content}

              {icon ? icon : null}
              {badgeUp ? (
                  <Badge color={badgeColor ? badgeColor : 'primary'} className='badge-sm badge-up' pill>
                    {badgeText ? badgeText : '0'}
                  </Badge>
              ) : null}
        </span>
        ) : (
            <img
                className={classnames({
                  [imgClassName as string]: imgClassName
                })}
                src={img as string}
                alt='avatarImg'
                height={imgHeight && !size ? imgHeight : 32}
                width={imgWidth && !size ? imgWidth : 32}
            />
        )}
        {status ? (
            <span
                className={classnames({
                  [`avatar-status-${status}`]: status,
                  [`avatar-status-${size}`]: size
                })}
            ></span>
        ) : null}
      </Tag>
  )
})

export default Avatar

// ** Default Props
Avatar.defaultProps = {
  tag: 'div'
}

// ** Prop Types for error handling (TypeScript equivalent)
const checkInitialsProp = (props: AvatarProps) => {
  if (props.initials && props.content === undefined) {
    return new Error('content prop is required with initials prop.')
  }
  if (props.initials && typeof props.content !== 'string') {
    return new Error('content prop must be a string.')
  }
  if (typeof props.initials !== 'boolean' && props.initials !== undefined) {
    return new Error('initials must be a boolean!')
  }
}
