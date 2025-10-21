import {useIntl, FormattedMessage} from 'react-intl';

import NetworkError from '../../../backend';

const ErrorDialog = ({error, onClose}) => {

    const intl = useIntl();

    if (error == null) {
        return null;
    }

    const modalStyle = {display: 'block'};
    let message;
    if (error instanceof NetworkError) {
        message = intl.formatMessage({ id: 'project.global.exceptions.NetworkError' });
    } else {
        // fallbacks seguros
        message =
            error.message ??
            error.globalError ??
            intl.formatMessage({ id: 'project.global.exceptions.UnexpectedError', defaultMessage: 'Se produjo un error' });
    }

    return (

        <div className="modal" style={modalStyle} tabIndex="-1" role="dialog">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">
                            <FormattedMessage id="project.common.ErrorDialog.title"/>
                        </h5>
                    </div>
                    <div className="modal-body">
                        <p>{message}</p>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-primary" 
                            data-dismiss="modal" 
                            onClick={onClose}>
                            <FormattedMessage id="project.global.buttons.close"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>

    );

};

export default ErrorDialog;
