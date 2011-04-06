<?php

/**
 * Company addresses actions
 *
 */
class companyAddressesActions extends myFrontModuleActions {

    public function executeFormWidget(dmWebRequest $request) {
        $address_id = null;
        $company_address = null;

        if ($request->hasParameter('address_id')) {
            $address_id = $request->getParameter('address_id');
        }
        if ($this->getUser()->hasAttribute('company_id')) {
            $this->company_id = $this->getUser()->getAttribute('company_id');

        } else {
            $this->getUser()->logError($this->getI18n()->__('No company selected'));
            $this->redirect($this->getHelper()->link('main/editMyCompany')->getHref());
        }
        
        if ($this->getPage()->getModuleAction() == 'supplier/editCompanyAddress')
                $this->getRequest()->setParameter('cancel_url', $this->getHelper()main/checkout')

        if ($this->company_id && $address_id) {

            $user_id = $this->getUser()->getDmUser()->getId();

            $q = DmDb::table('CompanyAddresses')->createQuery('ca')
                    ->innerJoin('ca.Company c')
                    ->innerJoin('c.Contacts cc ON cc.company_id = c.id')
                    ->andWhere('cc.user_id = ?', $user_id)
                    ->andWhere('ca.id = ?', $address_id)
                    ->andWhere('ca.company_id = ?', $this->company_id);
            $company_address = $q->fetchOne();
            if (!$company_address instanceof CompanyAddresses || $company_address->count() == 0) {
                $company_name = dmDb::table('Company')->find($this->company_id);
                if ($company_name) $company_name = $company_name->company_name;
                else $company_name = 'No company';
                $this->getUser()->logError(
                        $this->getI18n()->__('Address id "%address_id%"  is not available to company "%company%" for user "%user%"',
                        array('%address_id%' => $address_id,'%company%' => $company_name, '%user%' => $this->getUser()->getDmUser())));
                $this->redirect($this->getHelper()->link('main/editMyCompany')->getHref());
            }
} else {
    $company_address = new CompanyAddresses();
    $company_address->company_id = $this->company_id;
}

$form = new CompanyAddressesForm($company_address);


if ($request->isMethod('post') && $form->bindAndValid($request)) {
    $form->save();
    if ($form->isNew()) {
        $message = $this->getI18n()->__('Address has been successfully created');
        $this->redirect($this->getHelper()->link('main/editMyCompany')->getHref());
        $this->getUser()->logInfo($message);
    }
    else {
        $message = $this->getI18n()->__('Address has been successfully updated');
        $this->getUser()->logInfo($message);
    }


//            echo $request->getReferer().'<br>';
//            echo $request->getUri();

    $module_action = $this->getPage()->getModuleAction();
    if ($module_action == 'main/editCompanyAddress')
        $this->redirect ($this->getHelper()->link('main/editMyCompany')->getHref());

}
$this->forms['CompanyAddresses'] = $form;

}